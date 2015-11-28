package com.pcjoshi.chatter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * Created by prakash on 11/28/15.
 */
public class ChannelManager extends AbstractVerticle {
    Map<String,ChannelInfo> channelMap = new HashMap<>();

    @Override
    public void start(){
        new ChannelInfo("#general").registerWith(channelMap);
        vertx.eventBus().consumer("channels.list",msg ->{
            msg.reply(new JsonObject().put("channels",new JsonArray(channelMap.values().stream().map(ChannelInfo::toJson).collect(Collectors.toList()))));
        });
        vertx.eventBus().consumer("channels.join",(Message<JsonObject> msg)->{
            // sanity checking of values
            ChannelInfo channel = channelMap.computeIfAbsent(msg.body().getString("name"), ChannelInfo::new);
            msg.reply(channel.toJson());
        });
    }

    static class ChannelInfo{
        String name,address;

        ChannelInfo(String name){
            this.name = name;
            this.address = "channel."+name;
        }

        JsonObject toJson(){
            return  new JsonObject().put("address",address).put("name",name);
        }

        void registerWith(Map<String,ChannelInfo> channelMap){
            channelMap.put(name,this);
        }
    }
}

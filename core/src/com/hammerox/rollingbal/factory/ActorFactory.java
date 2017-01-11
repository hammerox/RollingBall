package com.hammerox.rollingbal.factory;

import com.hammerox.rollingbal.actors.Actor;

/**
 * Created by Mauricio on 11-Jan-17.
 */

public abstract class ActorFactory {

    abstract Actor createActor(Type type);

    public enum Type {
        CHARACTER,
        PLATFORM,
        DOUBLE_PLATFORM;
    }

}

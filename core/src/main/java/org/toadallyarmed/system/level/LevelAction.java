package org.toadallyarmed.system.level;

import org.toadallyarmed.util.action.Action;

public interface LevelAction <T extends Record> extends Action<T, BasicLevelActionPayload>  {
    boolean isDone();
}

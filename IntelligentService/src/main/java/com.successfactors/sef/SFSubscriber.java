package com.successfactors.sef;

/**
 * This is the standard interface for receive events from the service event bus from topics.
 * A subscriber can be registered to listen to one or more topics via the subscriber annotation.
 *
 *
 * A class must implement this interface and be located in the correct location for it to be registered to seam.
 * This component is found dynamically during runtime by a scanner.
 * @author ddiodati
 *
 */
public interface SFSubscriber {

    /**
     * Gets called when a message is posted to the topic this subscriber is listening to.
     * @param evt The SefEvent object.
     */
    void onEvent(SefEvent evt);

    /**
     * Gets called when there was some error during publishing of the event or during invocation of this subscriber.
     *
     * @param evt The message that failed.
     * @param e An exception that occurred (contained within this one).
     */
    void onError(SefEvent evt, Exception e);

}
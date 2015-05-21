package com.energizedwork.docker.config

/**
 * RestartPolicy – The behavior to apply when the container exits.
 */
class RestartPolicy {

    // The value is an object with a Name property of either "always" to always restart or "on-failure" to restart only when the container exit code is non-zero.
    String Name
    // If on-failure is used, MaximumRetryCount controls the number of times to retry before giving up.
    // The default is not to restart. (optional) An ever increasing delay (double the previous delay, starting at 100mS) is added before each restart to prevent flooding the server.
    Integer MaximumRetryCount

}

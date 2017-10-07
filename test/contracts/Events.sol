pragma solidity ^0.4.11;

contract Events {
    event Event(string indexed topic, string value);

    function emitEvent(string topic, string value) {
        Event(topic, value);
    }
}
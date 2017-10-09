pragma solidity ^0.4.11;

contract Events {
    event Event(string indexed topic, string value);
    event AddressEvent(address indexed topic, string value);

    function emitEvent(string topic, string value) {
        Event(topic, value);
    }

    function emitAddressEvent(address topic, string value) {
        AddressEvent(topic, value);
    }
}
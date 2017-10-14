pragma solidity ^0.4.11;

contract IntegrationTest {
    uint256 public state;

    function setState(uint256 _state) public {
        state = _state;
    }

    event SimpleEvent(string indexed topic, string value);
    event AddressEvent(address indexed topic, string value);
    event MixedEvent(address indexed topic, string value, address indexed test);

    function emitSimpleEvent(string topic, string value) {
        SimpleEvent(topic, value);
    }

    function emitAddressEvent(address topic, string value) {
        AddressEvent(topic, value);
    }

    function emitMixedEvent(address topic, string value, address test) {
        MixedEvent(topic, value, test);
    }
}
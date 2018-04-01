pragma experimental ABIEncoderV2;
pragma solidity ^0.4.21;

contract IntegrationTest {
    uint256 public state;

    function setState(uint256 _state) public {
        state = _state;
    }

    event SimpleEvent(string indexed topic, string value);
    event AddressEvent(address indexed topic, string value);
    event MixedEvent(address indexed topic, string value, address indexed test);
    event RateEvent(address token, uint256 value);

    struct Rate {
        address token;
        uint256 value;
    }

    function setRates(Rate[] rates) public {
        uint length = rates.length;
        for (uint i=0; i<length; i++) {
            RateEvent(rates[i].token, rates[i].value);
        }
    }

    Rate rate;

    function setRate(Rate _rate) public {
        rate = _rate;
        RateEvent(_rate.token, _rate.value);
    }

    function getRate() public returns (Rate) {
        return rate;
    }

    function emitSimpleEvent(string topic, string value) public {
        SimpleEvent(topic, value);
    }

    function emitAddressEvent(address topic, string value) public {
        AddressEvent(topic, value);
    }

    function emitMixedEvent(address topic, string value, address test) public {
        MixedEvent(topic, value, test);
    }
}
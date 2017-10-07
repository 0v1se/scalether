var Events = artifacts.require("./Events.sol");

module.exports = function(deployer) {
  deployer.deploy(Events);
};

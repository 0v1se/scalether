var IntegrationTest = artifacts.require("./IntegrationTest.sol");

module.exports = function(deployer) {
  deployer.deploy(IntegrationTest);
};

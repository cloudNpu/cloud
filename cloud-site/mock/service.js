const ServiceData = [
  {
    key: "1",
    app: "App1",
    ipAddr: "10.0.0.0",
    port: "8080",
    instanceId: "S1",
    appGroupName: "G1"
  },
  {
    key: "2",
    app: "App2",
    ipAddr: "10.0.0.0",
    port: "8080",
    instanceId: "S2",
    appGroupName: "G2"
  },
  {
    key: "3",
    app: "App3",
    ipAddr: "10.0.0.0",
    port: "8080",
    instanceId: "S3",
    appGroupName: "G3"
  },
  {
    key: "4",
    app: "App4",
    ipAddr: "10.0.0.0",
    port: "8080",
    instanceId: "S4",
    appGroupName: "G4"
  },
  {
    key: "5",
    app: "App5",
    ipAddr: "10.0.0.0",
    port: "8080",
    instanceId: "S5",
    appGroupName: "G5"
  },
  {
    key: "6",
    app: "App6",
    ipAddr: "10.0.0.0",
    port: "8080",
    instanceId: "S6",
    appGroupName: "G6"
  }
];

const getServiceData = {
  ServiceData
};

export default {
  //'GET /api/profile/advanced': getProfileAdvancedData,
  "GET /api/service": getServiceData
};

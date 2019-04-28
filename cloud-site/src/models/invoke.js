import { queryAppList } from "@/services/applist";

export default {
  namespace: "invokeTable",

  state: {
    InvokeData: []
  },

  effects: {
    *fetch(_, { call, put }) {
      const response = yield (yield call(queryAppList)).json();
      let visitData = [];
      for (let i = 0; i < response.length; i++) {
        visitData.push({
          appName: response[i].appName,
          instanceId: response[i].instanceId,
          invokeCount: response[i].invokeCount
        });
      }
      var a;
      for (let j = 0; j < visitData.length; j++) {
        for (let i = 0; i < visitData.length - j - 1; i++) {
          if (visitData[i].invokeCount < visitData[i + 1].invokeCount) {
            a = visitData[i];
            visitData[i] = visitData[i + 1];
            visitData[i + 1] = a;
          }
        }
      }
      yield put({
        type: "show",
        payload: {
          InvokeData: visitData
        }
      });
    }
  },

  reducers: {
    show(state, { payload }) {
      return {
        ...state,
        ...payload
      };
    }
  }
};

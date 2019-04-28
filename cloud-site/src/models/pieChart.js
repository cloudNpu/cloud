import { queryAnalysis } from "../services/analysis";

export default {
  namespace: "pieChart",

  state: {
    statusData: [],
    perData: 0
  },
  effects: {
    *fetchStatus({ payload }, { call, put }) {
      const response = yield yield call(queryAnalysis, payload);
      const status = (yield response.text()) + "}";
      var a = JSON.parse(status);
      var c = a.generalStats;
      let d = [];
      d.push(a.generalStats);
      yield put({
        type: "show",
        payload: {
          statusData: d,
          perData: 28
        }
      });
    }
  },
  reducers: {
    save(state, action) {
      return {
        ...state,
        data: action.payload
      };
    },
    show(state, { payload }) {
      return {
        ...state,
        ...payload
      };
    },
    clear() {
      return {};
    }
  }
};

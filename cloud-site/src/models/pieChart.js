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
      var e = JSON.stringify(c);
      //console.log(e);
      var f = e.substr(100, 3);
      if (f.charAt(f.length - 1) === ")") {
        f = f.substr(0, 2);
      }
      //console.log(f);
      //  (77%)(99(,100 7,101 7,103 %
      // console.log(JSON.parse(e).current-memory-usage);
      yield put({
        type: "show",
        payload: {
          statusData: d,
          perData: f
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

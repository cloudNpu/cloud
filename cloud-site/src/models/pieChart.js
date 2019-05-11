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
      let  a = JSON.parse(status);
      let c = a.generalStats;
      let d = [];
      d.push(a.generalStats);
      let currentUsage=c['current-memory-usage'];
      let str = JSON.stringify(currentUsage);
      for(let i=0;i<str.length;i++){
        if(str[i]==="("){
          var f=str.substr(i+1,3);
        }
      }
      // var f = e.substr(100, 3);
      // if (f.charAt(f.length - 1) === ")") {
      //   f = f.substr(0, 2);
      // }
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

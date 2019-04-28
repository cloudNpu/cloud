import { queryAppList } from "@/services/applist";
import { queryAnalysis } from "../services/analysis";

export default {
  namespace: "analysis",

  state: {
    data: {
      list: [],
      pagination: {}
    }
  },
  effects: {
    *fetch({ payload }, { call, put }) {
      const response = yield (yield call(queryAppList, payload)).json();
      yield put({
        type: "save",
        payload: {
          list: response,
          pagination: { pageSize: 4 }
        }
      });
    },
    *fetchStatus({ payload }, { call, put }) {
      const response = yield yield call(queryAnalysis, payload);
      const status = (yield response.text()) + "}";
      console.log(JSON.parse(status));
      yield put({
        type: "save",
        payload: {
          //list: response,
          //pagination: { pageSize: 4}
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
    clear() {
      return {};
    }
  }
};

import { updateCenter } from "../services/api";

export default {
  namespace: "center",

  state: {
    data: {
      list: [],
      currentUser: {}
    }
  },

  effects: {
    *update({ payload, callback }, { call, put }) {
      //console.log('update');
      //console.log(payload);
      const response = yield (yield call(updateCenter, payload)).json();
      yield put({
        type: "save",
        payload: response
      });
      if (callback) callback();
    }
  },

  reducers: {
    //处理所有同步逻辑
    save(state, action) {
      console.log(action.payload);
      return {
        ...state,
        data: action.payload //数据返回给页面
      };
    }
  }
};

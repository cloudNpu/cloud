import {
  addFound,
  queryFound,
  removeFound,
  updateFound,
  Add_user_role,
  Add_user_app
} from "../services/api";

export default {
  namespace: "found",

  state: {
    data: {
      list: [],
      pagination: {}
    }
  },

  effects: {
    *fetch({ payload }, { call, put }) {
      const response = yield (yield call(queryFound, payload)).json();
      yield put({
        type: "save",
        payload: response
      });
    },
    *add({ payload, callback }, { call, put }) {
      const response = yield (yield call(addFound, payload)).json();
      yield put({
        type: "save",
        payload: response
      });
      if (callback) callback();
    },
    *remove({ payload, callback }, { call, put }) {
      const response = yield (yield call(removeFound, payload)).json();
      yield put({
        type: "save",
        payload: response
      });
      if (callback) callback();
    },
    *update({ payload, callback }, { call, put }) {
      //console.log(payload);
      const response = yield (yield call(updateFound, payload)).json();
      yield put({
        type: "save",
        payload: response
      });
      if (callback) callback();
    },
    *add_user_role({ payload, callback }, { call, put }) {
      console.log(payload);
      const response = yield (yield call(Add_user_role, payload)).json();
      yield put({
        type: "save",
        payload: response
      });
      console.log(payload);
      if (callback) callback();
    },
    *add_user_app({ payload, callback }, { call, put }) {
      console.log(payload);
      const response = yield (yield call(Add_user_app, payload)).json();
      yield put({
        type: "save",
        payload: response
      });
      console.log(payload);
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

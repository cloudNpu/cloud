import {
  queryAppList,
  removeAppList,
  addAppList,
  updateAppList,
  changeAppList
} from "@/services/applist";
//import {changeAppList} from "../services/api";

export default {
  namespace: "applist",

  state: {
    data: {
      list: [],
      pagination: {}
    }
  },
  effects: {
    *fetch({ payload }, { call, put }) {
      const response = yield call(queryAppList, payload);
      yield put({
        type: "save",
        payload: response
      });
    },
    *add({ payload, callback }, { call, put }) {
      const response = yield call(addAppList, payload);
      yield put({
        type: "save",
        payload: response
      });
      if (callback) callback();
    },

    *remove({ payload, callback }, { call, put }) {
      const response = yield (yield call(removeAppList, payload)).json();
      yield put({
        type: "save",
        payload: response
      });
      if (callback) callback();
    },
    *update({ payload, callback }, { call, put }) {
      const response = yield (yield call(updateAppList, payload)).json();
      yield put({
        type: "save",
        payload: response
      });
      if (callback) callback();
    },

    //
    *change({ payload, callback }, { call, put }) {
      const response = yield (yield call(changeAppList, payload)).json();
      yield put({
        type: "save",
        payload: response
      });
      if (callback) callback();
    }
  },
  //
  reducers: {
    save(state, action) {
      return {
        ...state,
        data: action.payload
      };
    }
  }
};

import {
  queryAppList,
  removeAppList,
  addAppList,
  updateAppList,
  changeAppList
} from "@/services/api";
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
      const response = yield (yield call(queryAppList, payload)).json();
      //console.log(response);
      yield put({
        type: "save",
        payload: response
      });
    },
    *add({ payload, callback }, { call, put }) {
      console.log(payload);
      const response = yield (yield call(addAppList, payload)).json();
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

import {
  queryAppList,
  removeAppList,
  addAppList,
  updateAppList,
  changeAppList,
  appSearch,
  idSearch,
  visibleSearch,
  portSearch,
  ipAddrSearch
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
      const response = yield (yield call(queryAppList, payload)).json();
      yield put({
        type: "save",
        payload: {
          list: response,
          pagination: { pageSize: 6 }
        }
      });
      console.log(response);
    },
    *add({ payload, callback }, { call, put }) {
      console.log(payload);
      const response = yield call(addAppList, payload);
      yield put({
        type: "save",
        payload: {
          list: response,
          pagination: { pageSize: 6 }
        }
      });
      if (callback) callback();
    },

    *remove({ payload, callback }, { call, put }) {
      console.log(payload); //12,13,15
      const response = yield call(removeAppList, payload);
      yield put({
        type: "save",
        payload: {
          list: response,
          pagination: { pageSize: 6 }
        }
      });
      if (callback) callback();
    },
    *update({ payload, callback }, { call, put }) {
      const response = yield call(updateAppList, payload);
      yield put({
        type: "save",
        payload: {
          list: response,
          pagination: { pageSize: 6 }
        }
      });
      if (callback) callback();
    },
    *change({ payload, callback }, { call, put }) {
      console.log(payload);
      const response = yield call(changeAppList, payload);
      yield put({
        type: "save",
        payload: {
          list: response,
          pagination: { pageSize: 6 }
        }
      });
      if (callback) callback();
    },
    *app({ payload, callback }, { call, put }) {
      const response = yield (yield call(appSearch, payload)).json();
      yield put({
        type: "save",
        payload: {
          list: response,
          pagination: { pageSize: 6 }
        }
      });
      if (callback) callback();
    },
    *id({ payload, callback }, { call, put }) {
      const response = yield (yield call(idSearch, payload)).json();
      console.log(response);
      yield put({
        type: "save",
        payload: {
          list: response,
          pagination: { pageSize: 6 }
        }
      });
      if (callback) callback();
    },
    *visible({ payload, callback }, { call, put }) {
      console.log(payload);
      const response = yield (yield call(visibleSearch, payload)).json();
      yield put({
        type: "save",
        payload: {
          list: response,
          pagination: { pageSize: 6 }
        }
      });
      if (callback) callback();
    },
    *port({ payload, callback }, { call, put }) {
      console.log(payload);
      const response = yield (yield call(portSearch, payload)).json();
      yield put({
        type: "save",
        payload: {
          list: response,
          pagination: { pageSize: 6 }
        }
      });
      if (callback) callback();
    },
    *ipAddr({ payload, callback }, { call, put }) {
      console.log(payload);
      const response = yield (yield call(ipAddrSearch, payload)).json();
      yield put({
        type: "save",
        payload: {
          list: response,
          pagination: { pageSize: 6 }
        }
      });
      if (callback) callback();
    }
  },
  reducers: {
    save(state, action) {
      return {
        ...state,
        data: action.payload
      };
    }
  }
};

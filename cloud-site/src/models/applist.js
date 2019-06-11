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
    },
    *add({ payload, callback }, { call, put, select }) {
      const response = yield (yield call(addAppList, payload)).json();
      let list = yield select(state => state.applist.data.list);
      list.push(response);
      yield put({
        type: "save",
        payload: {
          list: list,
          pagination: { pageSize: 6 }
        }
      });
      if (callback) callback();
    },

    *remove({ payload, callback }, { call, put, select }) {
      console.log(payload);
      const response = yield call(removeAppList, payload);
      let list = yield select(state => state.applist.data.list);
      for (let i = 0, flag = true; i < list.length; flag ? i++ : i) {
        for (let j = 0; j < payload.length; j++) {
          if (list[i].id === payload[j]) {
            list.splice(i, 1);
            flag = false;
            break;
          } else {
            flag = true;
          }
        }
      } //好了，可以删掉31，33了
      yield put({
        type: "save",
        payload: {
          list: list,
          pagination: { pageSize: 6 }
        }
      });
      if (callback) callback();
    },
    *update({ payload, callback }, { call, put, select }) {
      const response = yield yield call(updateAppList, payload);
        console.log(response);
        //console.log(payload);
      let list = yield select(state => state.applist.data.list);
      yield list.forEach((value, index, array) => {
        /*let s = array[index];
        let res_app = response.body;
        if (
          s.appName === res_app.appName &&
          s.instanceId === res_app.instanceId
        ) {
          array[index] = res_app;
        }*/
        let user = array[index];
        if (
          user.appName === payload.appName &&
          user.instanceId === payload.instanceId
        ) {
          array[index] = payload;
        }
      });
      yield put({
        type: "save",
        payload: {
          list: list,
          pagination: { pageSize: 6 }
        }
      });
      if (callback) callback();
    },
    *change({ payload, callback }, { call, put, select }) {
      console.log(payload);
      const response = yield call(changeAppList, payload);
      let list = yield select(state => state.applist.data.list);
      yield list.forEach((value, index, array) => {
        let c = array[index];
        console.log(array[index].visible);
        if (c.appName === payload.appName) {
          array[index].visible = payload.visible;
        }
        /*if(user.id == payload .id) {
              array[index] = payload;
            }*/
      });
      yield put({
        type: "save",
        payload: {
          list: list,
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
      var list = [];
      list.push(response);
      yield put({
        type: "save",
        payload: {
          list: list,
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

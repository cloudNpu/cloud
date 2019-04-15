import {
  addRole,
  deleteRole,
  queryRole,
  updateRole,
  menuList
} from "@/services/rolesearch";

export default {
  namespace: "rolesearch",

  state: {
    data: {
      list: [],
      pagination: {}
    }
    /*,
          menu: []*/
  },

  effects: {
    *fetch({ payload }, { call, put }) {
      const response = yield (yield call(queryRole, payload)).json();
      yield put({
        type: "save",
        payload: {
          list: response
        }
      });
      // console.log(response);
    },
    *add({ payload, callback }, { call, put, select }) {
      const response = yield call(addRole, payload);
      //console.log(response);
      const list = yield select(state => state.rolesearch.data.list);
      list.push(payload);
      console.log(list);
      yield put({
        type: "save",
        payload: {
          list: list
          //   pagination: {}
        }
      });
      if (callback) callback();
    },

    *update({ payload, callback }, { call, put }) {
      const response = yield (yield call(updateRole, payload)).json();
      yield put({
        type: "save",
        payload: {
          list: response
          //   pagination: {}
        }
      });
      if (callback) callback();
    },

    *delete({ payload, callback }, { call, put }) {
      const response = yield call(deleteRole, payload);
      yield put({
        type: "save",
        payload: response
      });
      if (callback) callback();
    },

    *menu({ payload, callback }, { call, put }) {
      const response = yield call(menuList, payload);

      yield put({
        // type: "menuList",
        type: "save",
        // payload: Array.isArray(response) ? response : []
        payload: {
          list: response
          //   pagination: {}
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
    } /*,
        menuList(state, action) {
          return {
            ...state,
            menu: action.payload
          };
        }*/
  }
};

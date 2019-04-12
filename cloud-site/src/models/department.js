import {
  addDept,
  updateDept,
  deleteDept,
  queryDept
} from "@/services/department";
import { message } from "antd";

export default {
  namespace: "department",

  state: {
    data: {
      list: [],
      pagination: {}
    }
  },

  effects: {
    *fetch({ payload }, { call, put }) {
      const response = yield (yield call(queryDept, payload)).json();
      // console.log(response);
      yield put({
        type: "save",
        payload: {
          list: response
          // pagination: {}
        }
      });
    },
    *add({ payload, callback }, { call, put, select }) {
      const response = yield call(addDept, payload);
      //console.log(response);
      const list = yield select(state => state.department.data.list);
      // console.log(list);
      list.push(payload);
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
      const response = yield (yield call(updateDept, payload)).json();
      // console.log(response);
      yield put({
        type: "save",
        payload: {
          list: response
          // pagination: {}
        }
      });
      if (callback) callback();
    },
    *delete({ payload, callback }, { call, put }) {
      const response = yield call(deleteDept, payload);
      // console.log(response);
      yield put({
        type: "save",
        payload: {
          list: response
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

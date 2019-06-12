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
      const response = yield (yield call(addDept, payload)).json();
      // payload[0].id = response;
      console.log(response);
      const list = yield select(state => state.department.data.list);
      list.push(payload);
      //  console.log(list);
      list[list.length - 1]["id"] = response;
      // console.log(list);
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
    *delete({ payload, callback }, { call, put, select }) {
      const response = yield call(deleteDept, payload);
      // console.log(payload); //该删除行id
      const list = yield select(state => state.department.data.list);
      // console.log(list);  //删除前所有元素
      for (let i = 0, flag = true; i < list.length; flag ? i++ : i) {
        for (let j = 0; j < payload.ids.length; j++) {
          if (list[i].id === payload.ids[j]) {
            list.splice(i, 1);
            flag = false;
            break;
          } else {
            flag = true;
          }
        }
      }
      yield put({
        type: "save",
        payload: {
          list: list
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

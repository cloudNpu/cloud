import {routerRedux} from "dva/router";
import {stringify} from "qs";
import {fakeAccountLogin, getFakeCaptcha} from "@/services/api";
import {setAuthority} from "@/utils/authority";
import {getPageQuery} from "@/utils/utils";
import {reloadAuthorized} from "@/utils/Authorized";
import token from "../utils/token";
import user from "./user";

export default {
    namespace: "login",

    state: {
        status: undefined
    },

    effects: {
        * login({payload}, {call, put}) {
            const response = yield call(fakeAccountLogin, payload);
            const res = yield response.json();
           // console.log(res);
            // const res = response;
            yield put({
                type: "changeLoginStatus",
                payload: response
            });
            if (response.status !== 200) {
                yield put(routerRedux.push('/user/login'));
            }
            // Login successfully
            if (response.status === 200) {
                yield token.save(res.token);
                sessionStorage.setItem("user", JSON.stringify(res.user));
                //localStorage.setItem("currentUser", JSON.stringify(res.user));
                // var currentUser = JSON.parse(localStorage.getItem("user"));
                // var currentUser=JSON.parse(localStorage.getItem('user'));
                //console.log(currentUser);
                reloadAuthorized();
                const urlParams = new URL(window.location.href);
                const params = getPageQuery();
                let {redirect} = params;
                // console.log(sessionStorage.getItem("TOKEN"));
                // console.log(sessionStorage.getItem("user").id);
                if (redirect) {
                    const redirectUrlParams = new URL(redirect);
                    if (redirectUrlParams.origin === urlParams.origin) {
                        redirect = redirect.substr(urlParams.origin.length);
                        if (redirect.startsWith("/#")) {
                            redirect = redirect.substr(2);
                        }
                        /*if(redirect.match(/^\/.*#/)){
                         redirect=redirect.substr(redirect.indexOf('#')+1);
                       }*/
                    } else {
                        window.location.href = redirect;
                        return;
                    }
                }
                yield put(routerRedux.replace(redirect || "/"));
            }
        },

        * getCaptcha({payload}, {call}) {
            yield call(getFakeCaptcha, payload);
        },

        * logout(_, {put}) {
            yield put({
                type: "changeLoginStatus",
                payload: {
                    status: false,
                    currentAuthority: "guest"
                }
            });
            reloadAuthorized();
            yield put(
                routerRedux.push({
                    pathname: "/user/login",
                    search: stringify({
                        redirect: window.location.href
                    })
                })
            );
        }
    },

    reducers: {
        changeLoginStatus(state, {payload}) {
            //console.log(payload);
            setAuthority(payload.currentAuthority);
            return {
                ...state,
                status: payload.status,
                type: payload.type
            };
        }
    }
};

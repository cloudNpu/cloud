// // 这里模仿后台返回的菜单数据
// export default{
//     'GET /DynamicMenu/getDynamicMenu':[
//             // dashboard
//             { path: "/", redirect: "/dashboard/analysis" },
//             {
//                 path: "/dashboard",
//                 name: "dashboard",
//                 icon: "dashboard",
//                 routes: [
//                     {
//                         path: "/dashboard/analysis",
//                         name: "analysis",
//                         component: "./Dashboard/Analysis"
//                     }
//                 ]
//             },
//             //role
//             {
//                 path: "/role",
//                 icon: "meh",
//                 name: "role",
//                 routes: [
//                     {
//                         path: "/role/search",
//                         name: "search",
//                         component: "./Role/Search"
//                     }
//                 ]
//             },
//             // department
//             {
//                 path: "/department",
//                 icon: "solution",
//                 name: "department",
//                 routes: [
//                     {
//                         path: "/department/department",
//                         name: "department",
//                         component: "./Department/Department"
//                     }
//                 ]
//             },
//             //APP
//             {
//                 name: "app",
//                 icon: "smile",
//                 path: "/app",
//                 routes: [
//                     {
//                         path: "/app/search",
//                         name: "search",
//                         component: "./App/Search/AppList",
//                         routes: [
//                             {
//                                 path: "/app/search",
//                                 redirect: "/app/search/applist"
//                             }
//                         ]
//                     }
//                 ]
//             },
//             //users
//             {
//                 name: "users",
//                 icon: "team",
//                 path: "/users",
//                 routes: [
//                     {
//                         path: "/users/found",
//                         name: "found",
//                         component: "./Users/Found"
//                     }
//                 ]
//             },
//             //My
//             {
//                 name: "my",
//                 icon: "user",
//                 path: "/my",
//                 routes: [
//                     {
//                         path: "/my/center",
//                         name: "center",
//                         component: "./My/Center/Center",
//                         routes: [
//                             {
//                                 path: "/my/center",
//                                 component: "./My/Center/Center"
//                             }
//                         ]
//                     },
//                     {
//                         path: "/my/service",
//                         name: "service",
//                         component: "./My/Service/Service",
//                         routes: [
//                             {
//                                 path: "/my/service",
//                                 component: "./My/Service/Service"
//                             }
//                         ]
//                     }
//                 ]
//             },
//             //
//
//             //menu
//             {
//                 path: "/menus",
//                 icon: "menu-fold",
//                 name: "menus",
//                 routes: [
//                     {
//                         path: "/menus/first",
//                         name: "first",
//                         component: "./Menus/First"
//                     },
//                 ]
//             },
//             {
//                 name: "exception",
//                 icon: "warning",
//                 path: "/exception",
//                 routes: [
//                     // exception
//                     {
//                         path: "/exception/403",
//                         name: "not-permission",
//                         component: "./Exception/403"
//                     },
//                     {
//                         path: "/exception/404",
//                         name: "not-find",
//                         component: "./Exception/404"
//                     },
//                     {
//                         path: "/exception/500",
//                         name: "server-error",
//                         component: "./Exception/500"
//                     },
//                     {
//                         path: "/exception/trigger",
//                         name: "trigger",
//                         hideInMenu: true,
//                         component: "./Exception/TriggerException"
//                     }
//                 ]
//             },
//         {
//             component: '404',
//         },
//         // ],
//     // },
//     ],
//
// };
//
// // export default {
// //     'GET /DynamicMenu/getDynamicMenu': [
// //         // dashboard
// //         { path: '/', redirect: '/dashboard/analysis' },
// //         {
// //             path: '/dashboard',
// //             name: 'dashboard',
// //             icon: 'dashboard',
// //             routes: [
// //                 {
// //                     path: '/dashboard/analysis',
// //                     name: 'analysis',
// //                     component: './Dashboard/Analysis',
// //                 },
// //                 {
// //                     path: '/dashboard/monitor',
// //                     name: 'monitor',
// //                     component: './Dashboard/Monitor',
// //                 },
// //                 {
// //                     path: '/dashboard/workplace',
// //                     name: 'workplace',
// //                     component: './Dashboard/Workplace',
// //                 },
// //             ],
// //         },
// //         // forms
// //         {
// //             path: '/form',
// //             icon: 'form',
// //             name: 'form',
// //             routes: [
// //                 {
// //                     path: '/form/basic-form',
// //                     name: 'basicform',
// //                     component: './Forms/BasicForm',
// //                 },
// //                 {
// //                     path: '/form/step-form',
// //                     name: 'stepform',
// //                     component: './Forms/StepForm',
// //                     hideChildrenInMenu: true,
// //                     routes: [
// //                         {
// //                             path: '/form/step-form',
// //                             name: 'stepform',
// //                             redirect: '/form/step-form/info',
// //                         },
// //                         {
// //                             path: '/form/step-form/info',
// //                             name: 'info',
// //                             component: './Forms/StepForm/Step1',
// //                         },
// //                         {
// //                             path: '/form/step-form/confirm',
// //                             name: 'confirm',
// //                             component: './Forms/StepForm/Step2',
// //                         },
// //                         {
// //                             path: '/form/step-form/result',
// //                             name: 'result',
// //                             component: './Forms/StepForm/Step3',
// //                         },
// //                     ],
// //                 },
// //                 {
// //                     path: '/form/advanced-form',
// //                     name: 'advancedform',
// //                     authority: ['admin'],
// //                     component: './Forms/AdvancedForm',
// //                 },
// //             ],
// //         },
// //         // list
// //         {
// //             path: '/list',
// //             icon: 'table',
// //             name: 'list',
// //             routes: [
// //                 {
// //                     path: '/list/table-list',
// //                     name: 'searchtable',
// //                     component: './List/TableList',
// //                 },
// //                 {
// //                     path: '/list/basic-list',
// //                     name: 'basiclist',
// //                     component: './List/BasicList',
// //                 },
// //                 {
// //                     path: '/list/card-list',
// //                     name: 'cardlist',
// //                     component: './List/CardList',
// //                 },
// //                 {
// //                     path: '/list/search',
// //                     name: 'searchlist',
// //                     component: './List/List',
// //                     routes: [
// //                         {
// //                             path: '/list/search',
// //                             redirect: '/list/search/articles',
// //                         },
// //                         {
// //                             path: '/list/search/articles',
// //                             name: 'articles',
// //                             component: './List/Articles',
// //                         },
// //                         {
// //                             path: '/list/search/projects',
// //                             name: 'projects',
// //                             component: './List/Projects',
// //                         },
// //                         {
// //                             path: '/list/search/applications',
// //                             name: 'applications',
// //                             component: './List/Applications',
// //                         },
// //                     ],
// //                 },
// //             ],
// //         },
// //         {
// //             path: '/profile',
// //             name: 'profile',
// //             icon: 'profile',
// //             routes: [
// //                 // profile
// //                 {
// //                     path: '/profile/basic',
// //                     name: 'basic',
// //                     component: './Profile/BasicProfile',
// //                 },
// //                 {
// //                     path: '/profile/advanced',
// //                     name: 'advanced',
// //                     authority: ['admin'],
// //                     component: './Profile/AdvancedProfile',
// //                 },
// //             ],
// //         },
// //         {
// //             name: 'result',
// //             icon: 'check-circle-o',
// //             path: '/result',
// //             routes: [
// //                 // result
// //                 {
// //                     path: '/result/success',
// //                     name: 'success',
// //                     component: './Result/Success',
// //                 },
// //                 { path: '/result/fail', name: 'fail', component: './Result/Error' },
// //             ],
// //         },
// //         // {
// //         //     name: 'exception',
// //         //     icon: 'warning',
// //         //     path: '/exception',
// //         //     routes: [
// //         //         // exception
// //         //         {
// //         //             path: '/exception/403',
// //         //             name: 'not-permission',
// //         //             component: './Exception/403',
// //         //         },
// //         //         {
// //         //             path: '/exception/404',
// //         //             name: 'not-find',
// //         //             component: './Exception/404',
// //         //         },
// //         //         {
// //         //             path: '/exception/500',
// //         //             name: 'server-error',
// //         //             component: './Exception/500',
// //         //         },
// //         //         {
// //         //             path: '/exception/trigger',
// //         //             name: 'trigger',
// //         //             hideInMenu: true,
// //         //             component: './Exception/TriggerException',
// //         //         },
// //         //     ],
// //         // },
// //         {
// //             name: 'account',
// //             icon: 'user',
// //             path: '/account',
// //             routes: [
// //                 {
// //                     path: '/account/center',
// //                     name: 'center',
// //                     component: './Account/Center/Center',
// //                     routes: [
// //                         {
// //                             path: '/account/center',
// //                             redirect: '/account/center/articles',
// //                         },
// //                         {
// //                             path: '/account/center/articles',
// //                             component: './Account/Center/Articles',
// //                         },
// //                         {
// //                             path: '/account/center/applications',
// //                             component: './Account/Center/Applications',
// //                         },
// //                         {
// //                             path: '/account/center/projects',
// //                             component: './Account/Center/Projects',
// //                         },
// //                     ],
// //                 },
// //                 {
// //                     path: '/account/settings',
// //                     name: 'settings',
// //                     component: './Account/Settings/Info',
// //                     routes: [
// //                         {
// //                             path: '/account/settings',
// //                             redirect: '/account/settings/base',
// //                         },
// //                         {
// //                             path: '/account/settings/base',
// //                             component: './Account/Settings/BaseView',
// //                         },
// //                         {
// //                             path: '/account/settings/security',
// //                             component: './Account/Settings/SecurityView',
// //                         },
// //                         {
// //                             path: '/account/settings/binding',
// //                             component: './Account/Settings/BindingView',
// //                         },
// //                         {
// //                             path: '/account/settings/notification',
// //                             component: './Account/Settings/NotificationView',
// //                         },
// //                     ],
// //                 },
// //             ],
// //         },
// //         {
// //             component: '404',
// //         },
// //     ],
// // };

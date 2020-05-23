import { createStore,applyMiddleware,compose } from "redux";
import thunk from "redux-thunk";
import rootReducer from "./reducers";

const initialState={};
const middleware =[thunk];
 let store;
 if(window.navigator.userAgent.includes("chorme")){
store=createStore(
rootReducer, 
compose(applyMiddleware(...middleware),
 window.__REDUX_DEVTOOLS_EXTENSION__&&
 Window.__REDUX_DEVTOOLS_EXTENSION__()
 )
 );
}else{
    store=createStore(rootReducer,compose(applyMiddleware(...middleware)));
}
export default store;
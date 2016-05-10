/**
 * Created by freyjachang on 5/9/16.
 */
import { createStore, applyMiddleware } from 'redux'
import thunkMiddleware from 'redux-thunk'
import createLogger from 'redux-logger'
import reducer from "./../reducers/reducer"

export function configureStore(initialState) {
    const store = createStore(
        reducer,
        initialState,
        applyMiddleware(thunkMiddleware, createLogger())
    )

    //if (module.hot) {
    //    // Enable Webpack hot module replacement for reducers
    //    module.hot.accept('../reducers/reducer', () => {
    //        const nextRootReducer = require('../reducers/reducer').default
    //        store.replaceReducer(nextRootReducer)
    //    })
    //}

    return store
}
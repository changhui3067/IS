/**
 * Created by freyjachang on 5/2/16.
 */

export const SET_SUBCONFIG_SWITCHER = 'SET_SUBCONFIG_SWICHER'
export const SET_SUBTAB_FILTER = 'SET_SUBTAB_FILTER'
export const SET_SUBCONFIG = 'SET_SUBCONFIG'

export function handleSubConfigSwitchChanged(switcher) {
    return {
        type: SET_SUBCONFIG_SWITCHER,
        switcher: switcher
    }
}

export function handleSubConfigTabClick(tab) {
    return{
        type: SET_SUBTAB_FILTER,
        filter: tab
    }
}

export function receiveSubconfig() {
    return {
        type: SET_SUBCONFIG
    }
}

export function getSubConfig() {
    $.ajax({
        url: 'api/',
        type: "POST",
        //dataType: "json",
        contentType: "application/json",
        success: function(response){
            console.log('success')
            var json1 = response.json()
            dispatch(receiveSubconfig('frontend', json1))
        },//.bind(this),
        error: function(xhr, status, err){
            console.log(status, err.toString());
        }//.bind(this)
    })
}
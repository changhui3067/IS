/**
 * Created by freyjachang on 5/10/16.
 */
export const SET_EMPINFO = 'SET_EMPINFO'

export function receiveEmpInfo() {
    return {
        type: SET_EMPINFO
    }
}

export function getEmployeeInfo (menuItem) {
    $.ajax({
        url: 'api/',
        type: "POST",
        //dataType: "json",
        contentType: "application/json",
        success: function(response){
            console.log('success')
            var json1 = response.json()

            dispatch(receiveEmpInfo('frontend', json1))
        },//.bind(this),
        error: function(xhr, status, err){
            console.log(status, err.toString());
        }//.bind(this)
    })
}

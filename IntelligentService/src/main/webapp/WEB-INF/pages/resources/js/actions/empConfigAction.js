export const EMPCONFIG_ADD = 'EMPCONFIG_ADD'
export const SET_EMPCONFIG_DIALOG_VISIBLE = 'SET_EMPCONFIG_DIALOG_VISIBLE'
export const SET_EMPCONFIG_DELETE = 'SET_EMPCONFIG_DELETE'
export const SET_EMPCONFIG = 'SET_EMPCONFIG'

export function handleEmpConfigAddClick(filter) {
    return {
        type: EMPCONFIG_ADD
    }
}

export function handleEmpConfigSetDialogShow(visible, id) {
    return {
        type: SET_EMPCONFIG_DIALOG_VISIBLE,
        visible: visible,
        id: id
    }
}

export function handleEmpConfigDelete(id) {
    return {
        type: SET_EMPCONFIG_DELETE,
        id: id
    }
}

export function receiveEmpCofig() {
    return {
        type: SET_EMPCONFIG
    }
}

export function getEmpConfig() {
    $.ajax({
        url: 'api/',
        type: "POST",
        //dataType: "json",
        contentType: "application/json",
        success: function(response){
            console.log('success')
            var json1 = response.json()

            dispatch(receiveEmpConfig('frontend', json1))
        },//.bind(this),
        error: function(xhr, status, err){
            console.log(status, err.toString());
        }//.bind(this)
    })
}
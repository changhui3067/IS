export const SET_COURSECONFIG_FILTER = 'SET_COURSECONFIG_FILTER'
export const SET_COURSECONFIG_DELETE = 'SET_COURSECONFIG_DELETE'
export const SET_COURSECONFIG_DIALOG_VISIBLE = 'SET_COURSECONFIG_DIALOG_VISIBLE'
export const SET_COURSECONFIG = 'SET_COURSECONFIG'

export function handleCourseConfigTabClick(filter) {
    return {
        type: SET_COURSECONFIG_FILTER,
        filter: filter
    }
}

export function handleCourseConfigDelete(id) {
    return {
        type: SET_COURSECONFIG_DELETE,
        id
    }
}

export function handleCourseConfigSetDialogShow(visible, id) {
    return {
        type: SET_COURSECONFIG_DIALOG_VISIBLE,
        visible: visible,
        id: id
    }
}

export function receiveCourseConfig() {
    return {
        type: SET_COURSECONFIG
    }
}

export function getCourseConfig() {
    console.log('action, getCourseConfig')
    $.ajax({
        url: 'api/',
        type: "POST",
        //dataType: "json",
        contentType: "application/json",
        success: function(response){
            console.log('success')
            var json1 = response.json()
            receivePosts('frontend', json1)

        },//.bind(this),
        error: function(xhr, status, err){
            console.log(status, err.toString());
        }//.bind(this)
    })
}
/**
 * Created by freyjachang on 5/9/16.
 */
export const SET_PROPFILE_TEXT = 'SET_PROFILE_TEXT'

export function handleProfileSaveClick (field, text) {
    console.log('action: handleProfileSaveClick: ', field, text)
    return{
        type: 'SET_PROFILE_TEXT',
        field: field,
        text: text
    }
}
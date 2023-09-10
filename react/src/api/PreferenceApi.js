import { utilFetchWrapper } from "../services/utilFetchWrapper";
const fetchWrapper = utilFetchWrapper();

export function preferencesUser(userPreferences) {
    return fetchWrapper.post('/api/updatePreferences',userPreferences)
}

export function getPreferencesList() {
    return fetchWrapper.get('/api/preferences/all')
}

export function savePreferences(preferences) {
    return fetchWrapper.put(`/api/preferences`,preferences);
}

export function getUserPreferences() {
    return fetchWrapper.get('/api/myPreferences')
}
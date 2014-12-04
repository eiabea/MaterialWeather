MaterialWeather
===============

Weather Widget with Material Design

# UpdateService
## Description
Handles all the API requests to openweathermap.org. After a successful call it updates the database and also calls "onData" from all added UpdateInterfaces instances. If an error occures nothing is written into the database and "onError" gets called on all UpdateInterfaces

```
    public static final String PARAM_ACTION = "param_action";
    public static final String PARAM_SEARCH_STRING = "param_search_string";
    public static final String PARAM_CITY_ID = "param_city_id";
```
```
    public enum Action{
        RELOAD_ALL_CITIES,
        RELOAD_ONE_CITY,
        SEARCH_CITY
    }
```

- RELOAD_ALL_CITIES
    - Can be called without any additional parameters
- RELOAD_ONE_CITY
    - Have to be called with an additional intent extra "PARAM_CITY_ID" which includes the cityId of the city to update
- SEARCH_CITY
    - Have to be called with an additional intent extra "PARAM_SEARCH_STRING" which includes a searchstring e.g. "London, UK"
    
## Examples
### Reload all cities
```
Intent updateIntent = new Intent(getActivity(), UpdateService.class);
updateIntent.putExtra(
    UpdateService.PARAM_ACTION,
    UpdateService.Action.RELOAD_ALL_CITIES);
getActivity().startService(updateIntent);
```
### Reload one city
```
Intent updateIntent = new Intent(getActivity(), UpdateService.class);
    updateIntent.putExtra(
        UpdateService.PARAM_ACTION,
        UpdateService.Action.RELOAD_ONE_CITY);
    updateIntent.putExtra(
        UpdateService.PARAM_CITY_ID, cityId);
getActivity().startService(updateIntent);
```
### Search for city
```
Intent searchIntent = new Intent(getActivity(), UpdateService.class);
    searchIntent.putExtra(UpdateService.PARAM_ACTION,
        UpdateService.Action.SEARCH_CITY);
    searchIntent.putExtra(UpdateService.PARAM_SEARCH_STRING,
        "London, UK");
getActivity().startService(searchIntent);
```

* Create a freestyle job that runs PIT Descartes and generates a report (currently supports METHODS output format)
* Add a post build action to Generate STAMP Reporting views

The plugin is set by default to search for report files in the following search path
```
target/pit-reports/*/mutations.json
```
You can override this setting entering the advanced configuration menu.

You can also set the plugin to get a warning (mark the Build as _UNSTABLE_) if the Mutation coverage average is below a certain treshold.

Here is a snapshot of the configuration panel:

![Config panel](img/config.PNG?raw=true "Config panel")
 

# Jenkins STAMP report plugin
Jenkins plugin to visualize reports from STAMP tools.
Developed in the context of [STAMP project](https://stamp.ow2.org/)

* v0.1
Initial support for PIT Descartes Reports


## Install
* Install the plugin in Jenkins (for details on how to install a plugin see [here](https://jenkins.io/doc/book/managing/plugins/)).
You can download the released hpi file or build from source with 

```
mvn package
```

## Configure

* Create a freestyle job that runs PIT Descartes and generates a report (currently supports METHODS output format)
* Add a post build action to Generate STAMP Reporting views

###Advanced options

| Option  | Usage   | Default                                            |
| -------- | ------  | --------------------------------------------------- |
| `path`                   | search path for Descartes report files                                    | `target/pit-reports/*/mutations.json`    |
| `coverage treshold`  | get a warning (mark the Build as _UNSTABLE_) if the Mutation coverage average is below a certain treshold |`0`  (disables the option) |



The plugin is set by default to search for report files in the following search path
```
target/pit-reports/*/mutations.json
```
You can override this setting entering the advanced configuration menu.

You can also set the plugin to get a warning (mark the Build as _UNSTABLE_) if the Mutation coverage average is below a certain treshold.

Here is a snapshot of the configuration panel:

![Config panel](docs/img/config.PNG?raw=true "Config panel")
 



## Use 
After building your project yo will see STAMP Report trends in the Project main page:

![Overall View](docs/img/overall.PNG?raw=true "Overall View")

### Aggregated view

Following the "Aggregated results" link, a detailed view of the trends of the PIT Descartes Runs is shown 

![Project dashboard](docs/img/project.PNG?raw=true "Project dashboard")

### Build views

For each build, a menu item is created to show the detaild Report of the Descartes run.

![Build dashboard](docs/img/build.PNG?raw=true "Build dashboard")
 
 
 and the user can navigate through packages and classes to get detailed information on the mutation outcomes.
 
![Package view](docs/img/package.PNG?raw=true "Package view")

![Class view](docs/img/class.PNG?raw=true "Class view")

_Initial development by Engineering_
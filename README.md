# Jenkins STAMP report plugin
Jenkins plugin to visualize reports from STAMP tools.
Developed in the context of [STAMP project](https://stamp.ow2.org/)

* v0.1
Initial support for PIT Descartes Reports


## Getting Started
* Install the plugin in Jenkins (see [here](https://jenkins.io/doc/book/managing/plugins/)).
You can download the released hpi file or build from source with 

```
mvn package
```
## Usage 
* create a freestyle job that runs PIT Descartes and generates a report (currently supports METHODS output format)
* Add a post build action to Generate STAMP Reporting views

## Configure
You can configure the plugin to:
* overwrite the default file reports location path
* get a warning (Build result UNSTABLE) if the Mutation coverage average is below a certain treshold



_Initial development by Engineering_

#this is a simple etl toolkit project  

##initialize the project  
$> cd ${project-dir}  
$> mvn test  

##Project structure:  
this project has two workable modules:    
* etlt-expression  
    this module support a expression to do something, please run CmdMain to experience it.  
* etlt-core  
    this module depends on etlt-expression

#Release Note
##v1.0-0121-2
###add function: 
divide  
-- this function need 4 arguments: number, dividing number, scale, rounding mode in an integer
##v1.0-0419
###add function: 
length  
-- calculate length of a string
##v1.1
###add a new functionality
-- 1 file contains multi extractor settings with a suffix .exts, 1 file contains multi loader settings with a suffix .ldrs
##contact me  
[contact me using mail](mailto:juxiaomi@hotmail.com)
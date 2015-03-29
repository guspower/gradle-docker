! TODO

* Extend create container functionality to include
    * binds
    * links
    * lxc config
    * ports
    * dns
    * capabilities
    * volumes
    * restart policy
    * name / hostname / domainname
  
* Implement image history

* Define scenarios for gradle usage
    * create container from image before test run, stop and delete afterwards, optionally keep stats and logs
    * ensure container with specific id/name is running before test run, optionally stop it afterwards
    * create multiple containers before test run, wait for them to be ping-able (or other check)
  

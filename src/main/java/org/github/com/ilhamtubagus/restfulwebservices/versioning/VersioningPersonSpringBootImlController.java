package org.github.com.ilhamtubagus.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//public class VersioningPersonSpringBootImlController {
//    //sbi = spring boot implementation
////    Only one version strategy
////    @GetMapping(path = "/{version}/personsbi", version = "1.0.0")
////    public PersonV1 getFirstVersionOfPerson(){
////        return new PersonV1("Bob Charlie");
////    }
////
////    @GetMapping(path = "/{version}/personsbi", version = "2.0.0")
////    public PersonV2 getSecondVersionOfPerson(){
////        Name name = new Name("Bob", "Charlie");
////        return new PersonV2(name);
////    }
//
//    @GetMapping(path = "/personsbi", version = "1.0.0")
//    public PersonV1 getFirstVersionOfPersonRequestParams(){
//        return new PersonV1("Bob Charlie");
//    }
//
//    @GetMapping(path = "/personsbi", version = "2.0.0")
//    public PersonV2 getSecondVersionOfPersonRequestParams(){
//        Name name = new Name("Bob", "Charlie");
//        return new PersonV2(name);
//    }
//}

package org.github.com.ilhamtubagus.restfulwebservices.user;

import jakarta.validation.Valid;
import org.github.com.ilhamtubagus.restfulwebservices.jpa.PostRepository;
import org.github.com.ilhamtubagus.restfulwebservices.jpa.UserRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController()
@RequestMapping("/jpa")
public class UserJpaResource {

    private final UserRepository repository;

    private final PostRepository postRepository;

    public UserJpaResource(UserRepository repository, PostRepository postRepository) {
        this.repository = repository;
        this.postRepository = postRepository;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return this.repository.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUserById(@PathVariable int id){
        Optional<User> user = this.repository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("id:"+id);
        }

        // Wrap domain object and adding links to it
        EntityModel<User> entityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linkTo.withRel("all-users"));

        return entityModel;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = repository.save(user);
        // location - /users/4
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        this.repository.deleteById(id);
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrievePostsForUser(@PathVariable int id){
        Optional<User> user = this.repository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("id:"+id);
        }

        return user.get().getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Object> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post){
        Optional<User> user = this.repository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("id:"+id);
        }

        // set user to post
        post.setUser(user.get());

        // save the changes
        Post savedPost = this.postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedPost.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{id}/posts/{postId}")
    public EntityModel<Post> retrieveUserPostById(@PathVariable int id, @PathVariable int postId){
        Optional<User> user = this.repository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("id:"+id);
        }

        List<Post> posts = user.get().getPosts();
        Post post = posts.stream().filter(p -> p.getId() == postId).findFirst().orElse(null);

        // Wrap domain object and adding links to it
        assert post != null;
        EntityModel<Post> entityModel = EntityModel.of(post);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrievePostsForUser(id));
        entityModel.add(linkTo.withRel("all-posts"));

        return entityModel;
    }

}

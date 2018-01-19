# Domain Driven Tools Based on DCI Architecture For JAVA
# About Domain Driven
# About DCI Architecture

- The D for Data perspective is a “micro database” that represents the system state.
- The C for Context perspective specifies the configuration of a network of communicating
objects that implements a given system operation. The configuration is expressed as a
conforming network of objects and the links between them. The context also binds roles to
objects by selecting from the set of Data objects at run time. (This will be a select operation
in database terms, the context as a whole would be a kind of external view on a database)
- The I for Interaction perspective specifies how objects work together to realize a system
operation. The interaction is coded as role methods attached to the roles. For each object
that will play a role during its lifetime, we inject these methods into the object’s class.

# About Target

- **Basic** For most of coding cases, we have to use redundancy program code like CRUD to let the compute do right things.
The models in our project,are very likely a static data model --- which means the model only used to data transfer.
Obviously, it violate our original intention --- to build a world with coding.
This tool will concentrate on splitting logic code from other code,and using  object oriented programing  with domain driven design.

- **Simple** This project and tool are built to easy to import on any other java project.
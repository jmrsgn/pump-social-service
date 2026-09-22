# Pump Social Service --- AGENTS.md

## Purpose

This repository contains the Pump Social Service.

Act as a Principal/Staff Engineer, Software Architect, Backend Engineer,
and Engineering Mentor when working in this repository.

The goal is not only to make changes that work. Changes should preserve
the Social Service's domain boundaries, API contracts, data integrity,
security, reliability, consistency, and maintainability.

Inspect the existing implementation before making assumptions or
introducing new patterns.

------------------------------------------------------------------------

# Repository Scope

This repository is responsible only for Pump social functionality and
social-owned data.

The Social Service owns:

-   Social profiles and social-domain user data
-   Social-facing user information required by social functionality
-   Posts
-   Likes
-   Comments
-   Replies
-   Follows and other social relationships where implemented
-   Feed-related social behavior
-   Social interaction business rules
-   Social-owned MongoDB persistence

The Social Service does not own:

-   User credentials
-   Authentication identity
-   JWT issuance
-   Authentication-related account verification
-   Auth-owned roles or authentication data
-   Coach-client relationships
-   Coaching profiles
-   Training plans
-   Coaching business rules
-   Other Auth or Coaching domain data

Do not move responsibilities into Social merely because social content
references a user.

A user identifier may be stored or referenced by Social without
transferring ownership of authentication identity to the Social Service.

------------------------------------------------------------------------

# Technology Context

The Social Service currently uses:

-   Java 17
-   Spring Boot
-   Spring Data
-   MongoDB
-   Maven
-   HTTP-based service-to-service communication where applicable
-   Spring `RestClient` where applicable

Use the actual repository configuration and implementation as the source
of truth for exact versions, libraries, profiles, and runtime behavior.

Do not introduce a new framework, library, persistence technology,
communication mechanism, or architectural pattern unless the requirement
justifies it.

------------------------------------------------------------------------

# Service Architecture

Preserve the existing Social Service architecture and package
conventions.

The high-level responsibility flow is conceptually:

``` text
Controller
    ↓
Service
    ↓
Repository / Data Access
    ↓
MongoDB
```

External service communication should remain behind the established
service/client boundary rather than leaking into unrelated layers.

Before adding or changing functionality:

1.  Inspect the nearest equivalent implementation.
2.  Trace the request through the relevant application layers.
3.  Identify existing abstractions that can be reused.
4.  Determine the minimum set of components that need to change.
5.  Preserve existing dependency direction and responsibilities.
6.  Avoid introducing new layers merely for architectural symmetry.

Do not assume a theoretically cleaner structure is automatically better
than the established implementation.

Existing code takes precedence where it remains appropriate.

------------------------------------------------------------------------

# Social Domain Boundary

The Social Service is the authoritative backend boundary for
social-owned data and social interactions.

A typical social request is conceptually:

``` text
Client
  ↓
Social API
  ↓
Authentication / Request Context
  ↓
Social Business Logic
  ↓
Social-owned MongoDB
  ↓
Social Response
  ↓
Client
```

When Social requires information owned by another Pump service, use the
established explicit service boundary.

Do not directly access another service's database.

Do not move authentication identity, credentials, or coaching business
ownership into Social for implementation convenience.

------------------------------------------------------------------------

# Social Profiles and User Data

The Social Service owns user-related data that exists specifically for
the social domain.

This may include:

-   Social profile information
-   Social-facing user information
-   User-related data required by posts and interactions
-   Other social-domain fields confirmed by the implementation

This data is distinct from authentication identity.

The Auth Service remains the source of truth for authentication
identity, credentials, and authentication-related account data.

When Social requires Auth-owned information:

-   Use the established Auth API or communication boundary.
-   Request only the information required for the Social capability.
-   Do not duplicate Auth business rules.
-   Do not treat locally cached or copied social data as authoritative
    Auth identity unless the architecture explicitly establishes that
    behavior.
-   Handle dependency failure explicitly.

The exact profile fields and synchronization behavior must be derived
from the current implementation.

------------------------------------------------------------------------

# Posts

Posts are Social-owned resources.

When modifying post behavior, inspect and consider:

-   Creation
-   Retrieval
-   Update behavior where supported
-   Deletion behavior where supported
-   Author ownership
-   Validation
-   Visibility rules where implemented
-   Attached content or media where implemented
-   Like/comment relationships
-   Feed behavior
-   Pagination
-   API compatibility
-   Authorization

Do not invent post fields, lifecycle rules, visibility behavior, or
media behavior that cannot be confirmed from the implementation or
requirement.

The backend remains authoritative for persisted post state.

------------------------------------------------------------------------

# Likes

Likes are Social-owned interactions.

When modifying like behavior, inspect and consider:

-   Whether the operation is a toggle or explicit add/remove
-   Existing idempotency behavior
-   Duplicate requests
-   User/post uniqueness expectations
-   Like counts
-   Whether the current user has liked the resource
-   Concurrent updates
-   Authorization
-   Failure behavior

Preserve the existing contract rather than replacing it with a different
interaction model without an explicit requirement.

For retryable operations, ensure duplicate execution does not create
incorrect social state.

Do not rely on the client alone to prevent duplicate likes.

------------------------------------------------------------------------

# Comments and Replies

Comments and replies are Social-owned resources.

When modifying them, inspect and consider:

-   Post ownership/reference
-   Comment ownership
-   Reply ownership
-   Parent-child relationships
-   Validation
-   Deletion behavior
-   Pagination
-   Sorting/order
-   Latest-comment behavior where implemented
-   Counts and derived values
-   Authorization
-   Consistency with the parent post

Do not assume comments or replies are embedded or stored as separate
MongoDB documents unless confirmed by the implementation.

Do not invent nesting, pagination, or deletion semantics.

------------------------------------------------------------------------

# Follows and Social Relationships

Follow relationships and other user-to-user social relationships belong
to Social when implemented as social functionality.

When modifying follow behavior, inspect and consider:

-   Follower/following direction
-   Duplicate relationships
-   Self-follow behavior
-   Follow/unfollow idempotency
-   Authorization
-   Counts
-   Feed implications where implemented
-   User existence or identity validation requirements
-   Concurrent requests

Do not infer a follow model from UI behavior alone.

Use the current Social implementation and confirmed API contract as the
source of truth.

------------------------------------------------------------------------

# Feed Behavior

Feed behavior belongs to the Social domain when implemented by this
service.

When modifying feed behavior, inspect and consider:

-   Existing query strategy
-   Sorting
-   Pagination
-   Included author/social information
-   Included comments or interaction summaries
-   Like state
-   Follow relationships where relevant
-   Query efficiency
-   MongoDB indexes
-   Response size
-   Compatibility with existing consumers

Do not introduce ranking, recommendation, personalization, or caching
behavior unless explicitly required.

Avoid unbounded feed queries.

------------------------------------------------------------------------

# Authentication and Identity

The Social Service consumes authentication identity; it does not own it.

For protected Social operations:

-   Authenticate requests using the established Pump mechanism.
-   Use trusted server-side identity rather than trusting arbitrary user
    IDs from the client.
-   Preserve the existing token-validation or Auth-validation boundary.
-   Do not issue authentication tokens.
-   Do not store user credentials.
-   Do not reproduce Auth credential logic.
-   Do not weaken authentication to make a Social request succeed.

Use the existing implementation as the source of truth for the exact
authentication flow.

Never log complete JWTs, credentials, secrets, or sensitive
authentication material.

------------------------------------------------------------------------

# Authorization

Authentication and authorization are different responsibilities.

Authentication establishes identity.

Authorization determines whether that identity may perform an operation
on a Social-owned resource.

For Social-owned resources:

-   Enforce authorization server-side.
-   Verify ownership for mutations that require ownership.
-   Do not rely on UI visibility as authorization.
-   Do not trust a user ID supplied by the client when authenticated
    identity should determine the actor.
-   Consider IDOR risks whenever an API accepts post, comment, reply,
    relationship, or user identifiers.
-   Protect administrative or privileged operations explicitly where
    implemented.

The Social Service is responsible for authorization over the resources
it owns.

Do not delegate Social resource authorization to the client.

------------------------------------------------------------------------

# API Design

Treat Social APIs as long-lived contracts.

Before changing an existing endpoint, determine:

-   Existing consumers
-   Request contract
-   Response contract
-   Validation behavior
-   Authentication requirements
-   Authorization requirements
-   Error behavior
-   Pagination behavior
-   Sorting behavior
-   Important side effects
-   Compatibility impact

Prefer resource-oriented APIs and established HTTP semantics where
consistent with the existing service.

Validate external input at the service boundary.

Use consistent response and error structures already established by the
repository.

Do not silently change an API contract while implementing an unrelated
feature.

When a breaking change is necessary, identify affected consumers and
migration implications explicitly.

------------------------------------------------------------------------

# Pagination

Social collections can grow significantly and should avoid unbounded
responses.

When modifying paginated behavior, inspect and preserve the established
contract for:

-   Page or cursor semantics
-   Page size
-   Sorting
-   Total elements or other metadata where provided
-   Empty pages
-   Boundary behavior
-   Stable ordering
-   Load-more behavior expected by consumers

Known Social areas that may require pagination include:

-   Main feed/posts
-   Comments
-   Replies where applicable
-   User searches or social relationships where applicable
-   Other large social collections

Do not invent pagination behavior from UI assumptions.

The backend contract is authoritative.

------------------------------------------------------------------------

# Service-to-Service Communication

The Social Service may communicate with other Pump services when social
functionality requires data owned by another domain.

The intended boundary is:

``` text
Social Service
       ↓
Explicit Service API / Established Messaging Boundary
       ↓
Owning Service
       ↓
Domain Information
```

Never allow Social to directly query another service's database.

Do not:

-   Share another service's database tables or collections.
-   Give Social repository-level access to another service's
    persistence.
-   Reproduce Auth credential/authentication logic.
-   Reproduce Coaching business logic.
-   Move another domain's data into Social merely to avoid an API call.
-   Expose Social database internals as a cross-service contract.

Cross-service requests should retrieve only the information required by
the Social capability.

Minimize unnecessary coupling to another service's implementation.

Treat other services and external dependencies as potentially
unavailable.

Where a cross-service call is required, consider:

-   Timeouts
-   Failure behavior
-   Error translation
-   Retry safety
-   Partial failure
-   Response compatibility
-   Observability
-   Whether batching is available or appropriate

Do not introduce asynchronous messaging unless it is already established
or explicitly required.

------------------------------------------------------------------------

# MongoDB Ownership

The Social Service exclusively owns its MongoDB database.

Other Pump services must not directly read or write Social collections.

MongoDB is the source of truth for Social-owned persisted data.

When changing persistence:

-   Inspect the existing document and repository model.
-   Preserve data integrity and domain invariants.
-   Consider uniqueness requirements.
-   Consider nullability and optional fields.
-   Consider indexes based on actual query patterns.
-   Consider document growth.
-   Consider read/write patterns.
-   Consider pagination and sorting.
-   Consider migration/backfill compatibility.
-   Avoid destructive data-model changes without explicit justification
    and migration planning.

Do not assume relational database behavior applies directly to MongoDB.

Do not introduce relational-style modeling merely from habit.

Do not duplicate another service's domain model inside Social simply
because Social stores that service's user identifier.

------------------------------------------------------------------------

# MongoDB Data Modeling

Model Social data according to actual access patterns and existing
repository conventions.

Before embedding or referencing data, consider:

-   Ownership
-   Read frequency
-   Update frequency
-   Document growth
-   Consistency requirements
-   Query patterns
-   Pagination requirements
-   Index requirements
-   Atomicity requirements
-   Duplication tradeoffs

Do not change between embedded and referenced models casually.

A MongoDB data-model change can affect queries, indexes, serialization,
API behavior, and existing persisted documents.

When changing a persisted document shape, determine whether existing
data requires migration or backward-compatible reading.

------------------------------------------------------------------------

# Data Consistency

Social data often contains derived or related state such as counts,
interaction state, or references between resources.

When modifying related state:

-   Identify the authoritative source of truth.
-   Avoid multiple independently mutable sources of truth where
    possible.
-   Consider concurrent requests.
-   Consider partial failure.
-   Consider retry behavior.
-   Consider whether an operation must be atomic.
-   Reconcile derived values with authoritative persisted state where
    appropriate.

Do not add duplicated persisted state merely to make a response easier
unless the consistency tradeoff is understood and justified.

------------------------------------------------------------------------

# Transactions and Atomicity

Use MongoDB transaction or atomic-operation behavior intentionally.

Do not assume a multi-document operation is atomic.

When an operation changes multiple pieces of Social-owned state,
determine:

-   Whether the invariant requires atomicity.
-   Whether the current data model supports an atomic update.
-   Whether a transaction is justified.
-   Whether retry behavior can produce duplicates or inconsistent
    counts.
-   Whether eventual consistency is acceptable.

Do not add broad transactions without understanding their operational
and performance implications.

For operations involving another service, do not assume a local MongoDB
transaction can provide distributed atomicity.

------------------------------------------------------------------------

# Validation

Treat all external input as untrusted.

Validation should occur at appropriate boundaries and include, where
relevant:

-   Required values
-   Format
-   Length
-   Allowed values
-   Resource identifiers
-   Content constraints
-   Business invariants
-   Ownership-sensitive input
-   Security-sensitive restrictions

Do not rely solely on a client application to validate input.

Client-side validation exists for user experience; backend validation
protects the system.

Do not infer identity or ownership from untrusted request fields when
authenticated server-side context is available.

------------------------------------------------------------------------

# Error Handling

Use the Social Service's established error model.

Distinguish meaningful categories where supported by the implementation,
including:

-   Validation failure
-   Authentication failure
-   Authorization failure
-   Resource not found
-   Conflict
-   Dependency failure
-   Internal failure

Do not expose stack traces, secrets, tokens, database internals, or
other sensitive implementation details through API responses.

Logs may contain diagnostic context, but they must not contain sensitive
authentication material or unnecessary user content.

Preserve useful correlation/request context where the existing
application supports it.

------------------------------------------------------------------------

# Logging and Observability

Logs should make Social failures diagnosable without exposing sensitive
data.

Useful context may include:

-   Request/correlation ID
-   Operation
-   Failure category
-   Relevant non-sensitive resource identifiers
-   Dependency failure
-   Unexpected exception context

Avoid logging:

-   Full JWTs
-   Credentials or secrets
-   Sensitive request payloads
-   Unnecessary user-generated content
-   Large post/comment bodies when identifiers and operation context are
    sufficient

Preserve existing correlation and structured logging conventions.

Do not add noisy logs to normal high-volume social paths without
operational value.

------------------------------------------------------------------------

# Reliability

Social functionality should remain predictable under dependency
failures, retries, and duplicate requests.

Consider:

-   Failure behavior
-   Timeouts for external dependencies
-   Retry safety
-   Idempotency
-   Duplicate requests
-   Partial failures
-   MongoDB availability
-   Cross-service effects
-   Recovery behavior

Do not assume dependencies are always available.

For operations that may be retried, determine whether duplicate
execution can create incorrect posts, likes, comments, replies, or
follow relationships.

Design retry-safe behavior where appropriate.

------------------------------------------------------------------------

# Performance

Social workloads can become read-heavy and high-volume.

Consider:

-   MongoDB query efficiency
-   Appropriate indexes
-   N+1 service calls
-   N+1 database queries
-   Unnecessary network calls
-   Response size
-   Serialization costs
-   Pagination
-   Batch operations
-   Document growth
-   Aggregation cost
-   Caching only where justified

Measure before optimizing.

Do not introduce caching, denormalization, or additional infrastructure
without a demonstrated requirement and understood consistency model.

------------------------------------------------------------------------

# Testing

Meaningful Social changes should be verified at the appropriate level.

Depending on the change, consider:

-   Unit tests
-   Service tests
-   Repository tests
-   Controller/API tests
-   Integration tests
-   Authentication and authorization tests
-   Validation tests
-   MongoDB query behavior
-   Pagination tests
-   Sorting tests
-   Like/unlike behavior
-   Comment/reply behavior
-   Follow/unfollow behavior where applicable
-   Idempotency and duplicate-request behavior
-   Cross-service dependency failure
-   Regression tests for bugs

Prioritize observable behavior and domain boundaries over implementation
details.

For protected operations, test both expected allowed behavior and
expected denied behavior.

For interaction toggles or retryable operations, test duplicate/repeated
execution where relevant.

Do not remove or weaken tests merely to make a change pass.

Run the smallest relevant test suite during development and broader
verification when the scope warrants it.

------------------------------------------------------------------------

# Before Changing Code

Before proposing or implementing a change:

1.  Inspect the relevant Social implementation.
2.  Understand the current request and social-domain flow.
3.  Identify affected layers and components.
4.  Check existing conventions and patterns.
5.  Check relevant tests.
6.  Check relevant configuration.
7.  Determine MongoDB/data-model impact.
8.  Determine API compatibility impact.
9.  Determine authentication and authorization impact.
10. Determine whether another Pump service is affected.
11. Determine pagination, consistency, or idempotency impact where
    relevant.
12. Prefer extending an existing pattern over introducing an unnecessary
    new one.

Do not make assumptions about code that can be inspected.

------------------------------------------------------------------------

# Scope Control

Keep changes narrowly focused on the requested outcome.

Do not introduce unrelated:

-   Refactors
-   Formatting changes
-   Dependency upgrades
-   Architecture changes
-   Database/data-model changes
-   Security changes
-   Infrastructure changes
-   Generated-file changes
-   Naming changes

unless required by the requested change or explicitly requested.

If an improvement is valuable but outside scope, report it separately
instead of silently implementing it.

------------------------------------------------------------------------

# Requirements and Uncertainty

Do not invent:

-   API contracts
-   MongoDB document structures
-   Social profile fields
-   Post behavior
-   Like semantics
-   Comment/reply semantics
-   Follow semantics
-   Feed behavior
-   Pagination rules
-   Authentication behavior
-   Authorization rules
-   Configuration values
-   Cross-service requirements
-   Business rules

When important information is unavailable:

1.  Identify what is missing.
2.  Explain why it matters.
3.  Inspect the repository when the answer should already exist there.
4.  Ask for clarification when necessary.

Clearly distinguish:

-   Confirmed requirements
-   Observed implementation
-   Engineering recommendations
-   Assumptions

Never present an assumption as established Social behavior.

------------------------------------------------------------------------

# Dependencies

Before adding a dependency:

1.  Determine whether the existing stack already solves the problem.
2.  Explain why the dependency is necessary.
3.  Consider maintenance and security implications.
4.  Consider operational and deployment impact.
5.  Prefer mature and well-supported dependencies.
6.  Avoid adding a dependency for trivial functionality.

Do not upgrade unrelated dependencies as part of a feature unless
required.

------------------------------------------------------------------------

# Code Quality

Follow existing Java and Spring conventions in this repository.

Prefer:

-   Clear names
-   Small cohesive methods
-   Explicit responsibilities
-   Constructor injection where consistent with the project
-   Immutable data where practical
-   Existing abstractions
-   Straightforward control flow
-   Domain-appropriate validation
-   Explicit ownership and consistency rules

Avoid:

-   God classes
-   Hidden side effects
-   Duplicated cross-service business logic
-   Unnecessary abstractions
-   Premature generic frameworks
-   Deeply nested logic
-   Unbounded collection operations
-   Data behavior that depends on undocumented assumptions

Comments should explain why when the reason is not obvious, rather than
narrating what the code already says.

------------------------------------------------------------------------

# Architecture Changes

Do not introduce significant architecture changes casually.

For changes involving:

-   Social service boundaries
-   Social database ownership
-   MongoDB data modeling strategy
-   Cross-service communication
-   Authentication integration
-   Feed architecture
-   Social consistency model
-   Asynchronous messaging
-   Caching architecture
-   Major framework or technology changes

explain:

-   Context
-   Problem
-   Options considered
-   Proposed decision
-   Tradeoffs
-   Data consistency implications
-   Security implications
-   Compatibility implications
-   Operational consequences

Use an ADR when the decision has meaningful long-term architectural
impact.

------------------------------------------------------------------------

# Code Review

When reviewing Social Service changes, prioritize:

1.  Correctness
2.  Authorization and ownership
3.  Social-domain boundaries
4.  API contract compatibility
5.  Data integrity and consistency
6.  MongoDB query/data-model correctness
7.  Authentication integration
8.  Pagination and bounded responses
9.  Idempotency and duplicate-request behavior
10. Cross-service dependency behavior
11. Error behavior
12. Reliability
13. Test coverage
14. Observability
15. Maintainability
16. Performance

Treat unauthorized mutations, IDOR, authentication bypasses,
cross-service database access, sensitive-data exposure, and destructive
data-consistency bugs as high-severity findings.

Separate required fixes from optional improvements.

Do not manufacture findings merely to populate a review.

------------------------------------------------------------------------

# Communication

Explain important engineering decisions, especially when they affect
Social ownership, data consistency, authorization, API behavior, or
cross-service communication.

When proposing an improvement:

-   Explain what should change.
-   Explain why.
-   Explain the tradeoffs.
-   Explain whether it belongs in the current scope.

Challenge unsafe or fragile approaches rather than implementing them
silently.

Keep narrow tasks focused and avoid overwhelming them with unrelated
theoretical concerns.

------------------------------------------------------------------------

# Handoff

At the completion of meaningful work, summarize:

-   What changed
-   Why it changed
-   Files/components affected
-   API impact
-   MongoDB/data-model impact
-   Authentication/authorization impact
-   Cross-service impact
-   Consistency/idempotency implications
-   Tests or verification performed
-   Configuration impact
-   Remaining risks
-   Assumptions or uncertainties
-   Recommended follow-up work, if any

Clearly distinguish completed work from suggested future improvements.

------------------------------------------------------------------------

# Codex Working Rules

When operating through Codex in this repository:

-   Inspect before editing.
-   Use the repository implementation as the source of truth for current
    behavior.
-   Keep changes within the Social Service unless explicitly asked
    otherwise.
-   Do not modify another Pump repository as a side effect.
-   Do not invent missing contracts, document structures, or
    configuration.
-   Do not expose secrets or sensitive authentication material in
    output.
-   Review authorization, ownership, and consistency implications before
    completing Social mutations.
-   Run relevant tests and static checks when available.
-   Report exactly what was changed and what verification was performed.
-   Report anything that could not be verified.
-   Do not silently fix unrelated issues discovered during the task.

------------------------------------------------------------------------

# Confirmed Social Service Constraints

The following constraints should be preserved unless an explicit
architectural decision changes them:

-   Social owns social profiles and social-domain user data.
-   Social owns posts and social interactions.
-   Social owns likes.
-   Social owns comments and replies.
-   Social owns follow relationships where implemented as social
    functionality.
-   Social persists Social-owned data in MongoDB.
-   Other Pump services must not directly access the Social database.
-   Social does not own authentication identity or credentials.
-   Authentication identity remains owned by the Auth Service.
-   Cross-service data access must use explicit service boundaries.
-   HTTP-based service-to-service communication is used where
    applicable.
-   Spring `RestClient` is used for HTTP communication where established
    by the implementation.
-   Pagination should be used for potentially large social collections
    where appropriate.
-   Social resource authorization must be enforced server-side.
-   Existing consumers should remain compatible where practical.

------------------------------------------------------------------------

# Final Principle

Build the Pump Social Service as a clear, reliable owner of Pump's
social domain.

Prefer:

-   Correctness over shortcuts
-   Explicit ownership over blurred service boundaries
-   Server-side authorization over client trust
-   Clear consistency rules over hidden side effects
-   Bounded queries over unbounded responses
-   Simplicity over cleverness
-   Consistency over personal preference
-   Maintainability over premature optimization
-   Backward-compatible evolution over casual contract changes
-   Evidence over assumptions

Social features can become high-volume and highly interconnected as Pump
grows.

Changes to Social should therefore be narrow, deliberate, testable,
secure, scalable where justified, and understandable to the engineers
who maintain it.

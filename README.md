h1. Display Rule Versioning

h2. Overview

Currently, *form_ref_display_rule_group* stores *version_id*. However, the application fetches all active rule groups irrespective of the form version, making *version_id* ineffective.

To improve reusability and reduce duplicate configurations, introduce a new mapping table *form_ref_display_rule_group_version* and remove *version_id* from *form_ref_display_rule_group*.

----

h2. Problem Statement

* version_id is not used during rule retrieval.
* All active rule groups are processed, increasing overhead as more versions are introduced.
* Duplicate rule group configurations exist across multiple form versions, resulting in:
** Duplicate data
** Increased maintenance
** Reduced scalability

----

h2. Proposed Solution

Create a new mapping table *form_ref_display_rule_group_version* to associate *Form Versions* with *Display Rule Groups*.

This allows:
* Reuse of rule groups across multiple versions.
* Processing of only applicable rule groups.
* Elimination of duplicate configurations.

----

h2. Design

h3. Current

{code}
Form Version
      |
      V
form_ref_display_rule_group
      |
 version_id
{code}

h3. Proposed

{code}
Form Version
      |
      V
form_ref_display_rule_group_version
      |
      V
form_ref_display_rule_group
{code}

----

h2. Database Changes

h3. New Table

|| Column || Description ||
| id | Primary Key |
| version_id | Form Version ID |
| rule_group_id | Display Rule Group ID |
| active | Active Flag |
| created_by | Audit |
| created_date | Audit |
| updated_by | Audit |
| updated_date | Audit |

h3. Existing Table

* Remove *version_id* from *form_ref_display_rule_group*.

----

h2. Migration Plan

# Create *form_ref_display_rule_group_version* table.
# Migrate existing *version_id* values into the new mapping table.
# Update application logic to use the mapping table.
# Remove *version_id* after successful validation.

----

h2. Code Changes

* Update repository queries to use the mapping table.
* Update service layer to retrieve rule groups through the mapping table.
* Ensure mapping creation is idempotent for new form versions.

----

h2. SQL Scripts

h3. DDL

{code:sql}
-- CREATE TABLE script
{code}

h3. Migration Script

{code:sql}
-- Data migration script
{code}

h3. Rollback Script

{code:sql}
-- Rollback script
{code}

----

h2. Testing

* Validate existing forms.
* Verify only mapped rule groups are processed.
* Validate migrated data.
* Perform regression testing.

----

h2. Benefits

* Reusable rule groups.
* Reduced duplicate configurations.
* Improved maintainability.
* Better scalability.
* Reduced runtime overhead.
* Cleaner database design.

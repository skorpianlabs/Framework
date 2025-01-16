Feature: Login AS a Technician and Defer a Fault one

  Background:
    Given Technician is logged in
      | username | password | channel |
      | joha     | joha     | web     |

  Scenario: Validate Defer Fault flow in Web one
    Given Technician Raise a Fault
      | searchKey         | inputFieldText | channel | page |
      | My Aircraft Turns | P5-1524        | web     | turn |









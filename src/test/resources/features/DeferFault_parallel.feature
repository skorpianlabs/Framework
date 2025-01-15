Feature: Login AS a Technician and Defer a Fault two

  Scenario: Validate Defer Fault flow in Web two
    Given Technician Raise a Fault
      | username | password | searchKey         | inputFieldText | channel | page |
      | joha     | joha     | My Aircraft Turns | P5-1524        | web     | turn |



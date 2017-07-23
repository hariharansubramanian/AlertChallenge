# Alert Challenge
Myra Medicine backend alert challenge.

# Problem Statement

## Alerting System
There are many operations happening in the system which require monitoring. For example, when a transcription task is
assigned to a pharmacist in the call center, it has to be started within 1 minute and completed within 5 minutes. If the
timelines are not met, the concerned parties must be notified. The requirement is to build a system to monitor these events
and which is fast and robust.
The solution should implement three APIs


* ## Listing alerts
1. This is an API that will list all the alerts that have happened in the system and not yet been cleared.

2. It should output a list of alerts with reference_id , delay and description

```
Response:
Code: 200

{
    "alerts": [
                {
                    "reference_id": "transcription_start_1",
                    "delay": 60,
                    "description": "Transcription not yet started"
                },
                {
                    "reference_id": "transcription_complete_1",
                    "delay": 300,
                    "description": "Transcription not yet completed"
                }
            ]
}
```
* ## Posting an alert
1. This will take three parameters: reference_id , delay , description

2. The delay parameter is time in seconds after which the alert should appear in the listing API

```
Response:
Code: 201

{
    "alert": {
                "reference_id": "transcription_start_1",
                "delay": 60,
                "description": "Transcription not yet started"
    }
}
```
* ## Revoking an alert
1. This API will clear the alert from the system.
2. It will take reference_id as the parameter
3. It should be able to handle both cases, if an alert is already listed, it should be removed and if an alert is still within the
delay period, it should never be shown

```
Response:
Code: 204
{}
```
Expected load is 10 requests per second for each API. Response time should be within 100 ms.

## Deliverable requirements
1. You can choose stack of your preference.
2. It’s preferred to use macOS or Linux environment for coding if not then it must run on either of those.
3. It’s mandatory to use Git for version control.
4. We expect you to send us a zip/tarball of your source code which should include Git metadata (the .git folder in the
tarball so we can look at your commit logs and understand how your solution evolved).We expect you to do commits in
regular interval.
5. It’s mandatory to include a Readme file which must contain the following:
Overview of the tech stack, language etc.
Brief description of reason for using the specific tech stack
Infrastructure requirements for running your solution
Setup instructions, automated deployment of the program and dependencies to development and test environment
is a plus
6. It's a huge plus if you have written unit test cases and automated them.


# Solution Overview


# Installation


# Directory Structure


# API Reference


# Tests
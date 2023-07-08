package com.bikash.room

class MembersAlreadyExistsException: Exception(
    "There is already a member with that username in the room"
)
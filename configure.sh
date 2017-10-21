#!/usr/bin/env bash

# Make sure environment variables are defined on the server:
# CONFIG_FILE
# GROUP_ID
# CONFIRMATION_CODE
# TOKEN

sed -i -e "s/group.id=/group.id=$GROUP_ID/g" $CONFIG_FILE
sed -i -e "s/confirmation.code=/confirmation.code=$CONFIRMATION_CODE/g" $CONFIG_FILE
sed -i -e "s/token=/token=$TOKEN/g" $CONFIG_FILE

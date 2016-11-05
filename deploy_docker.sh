#!/bin/bash
rsync -avPr target/business_traceability/* rsync://localhost:10873/volume && \
docker exec -ti 01sars_wildfly_1 touch /opt/jboss/wildfly/standalone/deployments/ROOT.war.dodeploy
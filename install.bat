@echo.
@echo ----------------------------------------------------------------
@echo -------------------Cool-RPC  �������---------------------------
@echo ----------------------------------------------------------------

call mvn package

@echo.
@echo ----------------------------------------------------------------
@echo ---------------------Cool-RPC  ������-------------------------
@echo ----------------------------------------------------------------
@echo.

call mvn install:install-file -Dfile=target/cool-rpc-1.0.0.jar -DgroupId=com.cool -DartifactId=cool-rpc -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true

@echo.
@echo -------------------------------------------------------------------------
@echo -----------------------Cool-RPC  �ѵ��뱾�زֿ�--------------------------
@echo -------------------------------------------------------------------------
@echo.

call rd /s /Q target

@echo.
@echo ������Ŀpom�ļ����������������
@echo.
@echo.
@echo.
@echo ^<dependency^>
@echo     ^<groupId^>com.cool^</groupId^>
@echo     ^<artifactId^>cool-rpc^</artifactId^>
@echo     ^<version^>1.0.0^</version^>
@echo ^</dependency^>
@echo.
@echo.
@echo.

@pause